SUMMARY = "torsocks allows you to use most applications in a safe way with Tor"
HOMEPAGE = "https://www.torproject.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=baae225a2d047795fd2daf1aa8d74d43"

SRC_URI = "git://gitlab.torproject.org/tpo/core/${BPN};branch=main;protocol=https;tag=v${PV} \
           "
SRCREV = "7868aab684ce503ed7efa72a0d79e41a8f67ac65"

inherit autotools pkgconfig

INSANE_SKIP:${PN} = "dev-so"

BBCLASSEXTEND = "native nativesdk"
