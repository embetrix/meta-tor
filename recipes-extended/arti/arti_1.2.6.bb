SUMMARY = "Rust implementation of Tor"
HOMEPAGE = "https://www.torproject.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE-MIT;md5=94711c8b3680c34cc4fd83f09ab76644"

SRC_URI = "git://gitlab.torproject.org/tpo/core/${BPN};branch=main;protocol=https \
           "
SRCREV = "15e0b8d6702125274aa1844958e2293adb9f9d1d"
S = "${WORKDIR}/git"

DEPENDS = "openssl sqlite3"

inherit cargo cargo-update-recipe-crates pkgconfig

require ${BPN}-crates.inc
