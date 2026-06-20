# i18n CSV Sync Guide

This document explains how to sync `i18n/strings.csv` to locale-specific `strings.xml` files using Python scripts (with optional Gradle wrappers).

## Quick Start

Run either command from the project root:

```bash
# Gradle
./gradlew :app:syncI18nFromCsv

# Or Python directly
python3 i18n/sync-from-csv.py
```

The script reads the CSV and writes `app/src/main/res/values*/strings.xml`.

To export from XML back to CSV:

```bash
./gradlew :app:exportI18nToCsv
# or
python3 i18n/export-to-csv.py
```

## CSV Format

| Column | Required | Description |
|--------|----------|-------------|
| `key` | Yes | String resource name; maps to `name` in `strings.xml` |
| `translatable` | Yes | `true` or `false`; when `false`, written only to default `values/strings.xml` |
| `en` | Yes | English (default language) |
| `zh-CN` … | No | Translations per locale; empty cells fall back to the `en` value |

**Add a new string:** append a row to the CSV and run the sync command again.

**Add a new locale:** add a column to the CSV header and register the mapping in `LOCALE_RES_DIRS` inside `i18n/i18n_common.py`, for example:

```python
"es": "values-es",
```

## Sync Rules

1. **`values/strings.xml` (default)** — contains every key from the CSV
2. **Locale directories** — contain only keys where `translatable=true`
3. **`translatable=false`** — appears only in default `values/`, not in `values-zh-rCN` and similar folders
4. **Placeholders** — multiple `%s` / `%d` specifiers automatically add `formatted="false"`
5. **HTML** — supports `<u>...</u>` tags (e.g. underlined placeholders)
6. **Overwrite policy** — each sync **rewrites the full file**; keys removed from the CSV are removed from XML

## Recommended Workflow

```
Edit i18n/strings.csv  →  ./gradlew :app:syncI18nFromCsv  →  build / run to verify
```

## Folder Layout

```
i18n/
├── README.md           # This guide
├── strings.csv         # Source of truth for all locales
├── i18n_common.py      # Shared constants and XML helpers
├── sync-from-csv.py    # CSV → strings.xml
└── export-to-csv.py    # strings.xml → CSV
```

## Gradle Wrappers

| Task | Purpose |
|------|---------|
| `:app:syncI18nFromCsv` | CSV → strings.xml |
| `:app:exportI18nToCsv` | strings.xml → CSV |

Defined in `app/build.gradle.kts`.
