SUMMARY  = "Rust implementation of Tor"
HOMEPAGE = "https://www.torproject.org"
LICENSE  = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE-MIT;md5=ea15a04c32109e6f2b43111e74909027"

SRC_URI = "git://gitlab.torproject.org/tpo/core/${BPN};branch=main;protocol=https \
           file://arti.service.in \
           file://arti.toml \
           "
SRCREV = "7c038125baef924a4bf287429c192c5af606f79c"

DEPENDS = "openssl sqlite3"

inherit cargo cargo-update-recipe-crates pkgconfig

require ${BPN}-crates.inc

inherit useradd

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "--system arti"
USERADD_PARAM:${PN}  = "--system  --no-create-home -g arti -s /bin/false arti"

inherit ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)}
SYSTEMD_SERVICE:${PN} = "arti.service"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"

do_install:append() {

    install -d ${D}${sysconfdir}/arti
    install -m 0644 ${UNPACKDIR}/arti.toml ${D}${sysconfdir}/arti/arti.toml

    install -m 770 -d ${D}${localstatedir}/arti
    chown arti:arti ${D}${localstatedir}/arti

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${UNPACKDIR}/arti.service.in ${D}${systemd_system_unitdir}/arti.service
        sed -i 's:@bindir@:${bindir}:' ${D}${systemd_system_unitdir}/arti.service
    fi
}

FILES:${PN} += "${sysconfdir}/arti \
                ${localstatedir}/arti \
                "
