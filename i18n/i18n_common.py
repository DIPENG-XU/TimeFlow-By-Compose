"""Shared constants and helpers for i18n CSV <-> strings.xml sync."""

from __future__ import annotations

import re
from dataclasses import dataclass
from pathlib import Path

# CSV column name -> Android res directory under app/src/main/res/
LOCALE_RES_DIRS: dict[str, str] = {
    "en": "values",
    "zh-CN": "values-zh-rCN",
    "zh-HK": "values-zh-rHK",
    "zh-TW": "values-zh-rTW",
    "ja": "values-ja",
    "ko": "values-ko-rKR",
    "de": "values-de",
    "fr": "values-fr",
}

I18N_DIR = Path(__file__).resolve().parent
PROJECT_ROOT = I18N_DIR.parent
RES_DIR = PROJECT_ROOT / "app" / "src" / "main" / "res"
CSV_PATH = I18N_DIR / "strings.csv"

U_TAG_PATTERN = re.compile(r"<u>(.*?)</u>")


def strings_xml_path(locale: str) -> Path:
    return RES_DIR / LOCALE_RES_DIRS[locale] / "strings.xml"


@dataclass
class I18nRow:
    key: str
    translatable: bool
    values_by_locale: dict[str, str]

    def resolve_value(self, locale: str) -> str:
        direct = self.values_by_locale.get(locale, "").strip()
        if direct:
            return direct
        return self.values_by_locale.get("en", "").strip()


def count_format_specifiers(value: str) -> int:
    count = 0
    i = 0
    while i < len(value):
        if value[i] == "%" and i + 1 < len(value):
            nxt = value[i + 1]
            if nxt == "%":
                i += 2
            elif nxt in "sdfS":
                count += 1
                i += 2
            else:
                i += 1
        else:
            i += 1
    return count


def needs_formatted_false(value: str) -> bool:
    return count_format_specifiers(value) > 1


def escape_xml_text(text: str) -> str:
    return (
        text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("'", "\\'")
    )


def format_string_body(value: str) -> str:
    """Preserve Android-supported <u> tags; XML-escape all other text."""
    if not U_TAG_PATTERN.search(value):
        return escape_xml_text(value)

    parts: list[str] = []
    last_index = 0
    for match in U_TAG_PATTERN.finditer(value):
        parts.append(escape_xml_text(value[last_index : match.start()]))
        parts.append("<u>")
        parts.append(escape_xml_text(match.group(1)))
        parts.append("</u>")
        last_index = match.end()
    parts.append(escape_xml_text(value[last_index:]))
    return "".join(parts)
