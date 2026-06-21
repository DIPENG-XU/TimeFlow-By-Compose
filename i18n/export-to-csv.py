#!/usr/bin/env python3
"""Export i18n/strings.csv from strings.xml (reverse of sync-from-csv.py)."""

from __future__ import annotations

import csv
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent))

from i18n_common import CSV_PATH, LOCALE_RES_DIRS, strings_xml_path


def elem_inner_xml(elem: ET.Element) -> str:
    parts = [elem.text or ""]
    for child in elem:
        parts.append(f"<{child.tag}>")
        if child.text:
            parts.append(child.text)
        if child.tail:
            parts.append(child.tail)
        parts.append(f"</{child.tag}>")
    return "".join(parts).strip()


def parse_strings(path) -> dict:
    if not path.exists():
        return {}
    root = ET.parse(path).getroot()
    result = {}
    for elem in root.findall("string"):
        name = elem.get("name")
        if name:
            result[name] = {
                "value": elem_inner_xml(elem),
                "translatable": elem.get("translatable", "true"),
            }
    return result


def main() -> None:
    all_data: dict[str, dict] = {}
    for locale in LOCALE_RES_DIRS:
        for key, meta in parse_strings(strings_xml_path(locale)).items():
            if key not in all_data:
                all_data[key] = {"translatable": meta["translatable"]}
            all_data[key][locale] = meta["value"]

    def sort_key(k: str):
        t = all_data[k].get("translatable", "true")
        return (0 if t != "false" else 1, k)

    sorted_keys = sorted(all_data.keys(), key=sort_key)
    locales = list(LOCALE_RES_DIRS.keys())
    CSV_PATH.parent.mkdir(parents=True, exist_ok=True)

    with CSV_PATH.open("w", newline="", encoding="utf-8-sig") as f:
        writer = csv.writer(f)
        writer.writerow(["key", "translatable"] + locales)
        for key in sorted_keys:
            row = [key, all_data[key].get("translatable", "true")]
            for loc in locales:
                val = all_data[key].get(loc, "")
                if not val and loc != "en":
                    val = all_data[key].get("en", "")
                row.append(val)
            writer.writerow(row)

    print(f"Exported {len(sorted_keys)} keys to {CSV_PATH}")


if __name__ == "__main__":
    main()
