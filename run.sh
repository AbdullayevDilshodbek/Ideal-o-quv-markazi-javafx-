#!/usr/bin/env bash
# Loyihani ishga tushiradi: .env dagi o'zgaruvchilarni yuklab, `mvn javafx:run` ni chaqiradi.
set -euo pipefail

# Ilova text.css va .xls fayllarini joriy papkada o'qiydi/yozadi, shuning uchun loyiha ildizida ishlaymiz
cd "$(dirname "$0")"

if [[ -f .env ]]; then
    set -a
    source .env
    set +a
else
    echo "Ogohlantirish: .env topilmadi, standart qiymatlar ishlatiladi (.env.example dan nusxa oling)" >&2
fi

if ! command -v mvn >/dev/null 2>&1; then
    echo "Xato: Maven (mvn) topilmadi. O'rnatish: brew install maven" >&2
    exit 1
fi

exec mvn -q javafx:run "$@"
