SUMMARY = "Tor Onion Router core"
DESCRIPTION = "Tor Onion Router core to protect your privacy on the internet by hiding the connection between \
your Internet address and the services"
HOMEPAGE = "https://www.torproject.org"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1c49e60ec3ca90464faf1a4d6c19e384"

SRC_URI = "git://gitlab.torproject.org/tpo/core/${BPN};branch=release-0.4.9;protocol=https \
           file://tor.service.in \
           file://torrc \
           file://torrc.relay \
           "
SRCREV = "c51547ca15ea665950dd154e160051c57f5719db"

DEPENDS = "openssl libevent"
inherit autotools pkgconfig

PACKAGECONFIG ??= "systemd zstd lzma"
PACKAGECONFIG[systemd] = "--enable-systemd,--disable-systemd, systemd"
PACKAGECONFIG[zstd]    = "--enable-zstd,--disable-zstd, zstd"
PACKAGECONFIG[lzma]    = "--enable-lzma,--disable-lzma, xz"

EXTRA_OECONF += " --disable-tool-name-check --disable-manpage \
                  --disable-asciidoc --disable-html-manual --disable-unittests"

inherit useradd

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "--system tor"
USERADD_PARAM:${PN}  = "--system  --no-create-home -g tor -s /bin/false tor"

inherit ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)}
SYSTEMD_SERVICE:${PN} = "tor.service"
SYSTEMD_PACKAGES = "${PN}"

do_install:append() {

    install -d ${D}${sysconfdir}/tor
    install -m 0644 ${UNPACKDIR}/torrc ${D}${sysconfdir}/tor/torrc
    install -m 0644 ${UNPACKDIR}/torrc.relay ${D}${sysconfdir}/tor/torrc.relay

    install -m 770 -d ${D}${localstatedir}/tor
    chown tor:tor ${D}${localstatedir}/tor

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${UNPACKDIR}/tor.service.in ${D}${systemd_system_unitdir}/tor.service
        sed -i 's:@bindir@:${bindir}:' ${D}${systemd_system_unitdir}/tor.service
    fi
}

FILES:${PN} += "${bindir} \
                ${sysconfdir}/tor \
                ${datadir}/tor \
                ${localstatedir}/tor \
                "

RRECOMMENDS:${PN} += "torsocks"

BBCLASSEXTEND = "native nativesdk"
