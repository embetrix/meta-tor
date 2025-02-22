SUMMARY = "torsocks allows you to use most applications in a safe way with Tor"
HOMEPAGE = "https://www.torproject.org"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=baae225a2d047795fd2daf1aa8d74d43"

SRC_URI = "git://gitlab.torproject.org/tpo/core/${BPN};branch=main;protocol=https \
           "
SRCREV = "afe9dea542a8b495dbbbbe5e4b98a33cde06729b"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

FILES:${PN}-dev += "${libdir}/torsocks/libtorsocks.so"

BBCLASSEXTEND = "native nativesdk"
