#!/usr/bin/env python3
"""Sync i18n/strings.csv to app/src/main/res/values*/strings.xml."""

from __future__ import annotations

import csv
import sys
from dataclasses import dataclass
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent))

from i18n_common import (
    CSV_PATH,
    I18nRow,
    LOCALE_RES_DIRS,
    RES_DIR,
    format_string_body,
    needs_formatted_false,
)


@dataclass
class StringEntry:
    name: str
    value: str
    translatable: bool


def read_csv(csv_path: Path) -> list[I18nRow]:
    with csv_path.open(newline="", encoding="utf-8-sig") as f:
        rows = list(csv.reader(f))

    if len(rows) < 2:
        raise SystemExit(f"CSV must contain a header and at least one data row: {csv_path}")

    header = [cell.strip().lstrip("\ufeff") for cell in rows[0]]
    if header[0] != "key":
        raise SystemExit("First CSV column must be 'key'")
    if header[1] != "translatable":
        raise SystemExit("Second CSV column must be 'translatable'")

    locale_columns = header[2:]
    unknown = set(locale_columns) - set(LOCALE_RES_DIRS)
    if unknown:
        raise SystemExit(
            "Unmapped locale columns in CSV: "
            f"{', '.join(sorted(unknown))}. "
            "Add them to LOCALE_RES_DIRS in i18n/i18n_common.py."
        )

    result: list[I18nRow] = []
    for cells in rows[1:]:
        if not cells or not cells[0].strip():
            continue
        key = cells[0].strip()
        translatable = cells[1].strip().lower() != "false" if len(cells) > 1 else True
        values = {}
        for idx, locale in enumerate(locale_columns, start=2):
            values[locale] = cells[idx].strip() if idx < len(cells) else ""
        result.append(I18nRow(key=key, translatable=translatable, values_by_locale=values))
    return result


def build_string_element(entry: StringEntry) -> str:
    attrs = [f'name="{entry.name}"']
    if not entry.translatable:
        attrs.append('translatable="false"')
    if needs_formatted_false(entry.value):
        attrs.append('formatted="false"')
    body = format_string_body(entry.value)
    return f'    <string {" ".join(attrs)}>{body}</string>'


def write_strings_xml(
    values_dir: Path,
    entries: list[StringEntry],
    blank_line_after_resources: bool = False,
) -> None:
    values_dir.mkdir(parents=True, exist_ok=True)
    lines = ['<?xml version="1.0" encoding="utf-8"?>', "<resources>"]
    if blank_line_after_resources:
        lines.append("")
    lines.extend(build_string_element(entry) for entry in entries)
    lines.append("</resources>")
    (values_dir / "strings.xml").write_text("\n".join(lines) + "\n", encoding="utf-8")


def write_all(rows: list[I18nRow]) -> None:
    default_entries = [
        StringEntry(
            name=row.key,
            value=row.resolve_value("en"),
            translatable=row.translatable,
        )
        for row in rows
    ]
    write_strings_xml(RES_DIR / LOCALE_RES_DIRS["en"], default_entries)

    for locale, dir_name in LOCALE_RES_DIRS.items():
        if locale == "en":
            continue
        locale_entries = [
            StringEntry(name=row.key, value=row.resolve_value(locale), translatable=True)
            for row in rows
            if row.translatable
        ]
        write_strings_xml(RES_DIR / dir_name, locale_entries, blank_line_after_resources=True)


def main() -> None:
    csv_path = Path(sys.argv[1]) if len(sys.argv) > 1 else CSV_PATH
    if not csv_path.is_file():
        raise SystemExit(f"CSV file not found: {csv_path}")

    rows = read_csv(csv_path)
    write_all(rows)
    print(
        f"Synced {len(rows)} strings from {csv_path.name} "
        f"to {RES_DIR} ({len(LOCALE_RES_DIRS)} locale directories)"
    )


if __name__ == "__main__":
    main()
