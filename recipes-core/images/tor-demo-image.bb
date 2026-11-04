DESCRIPTION = "Tor Demo Image"

inherit core-image

EXTRA_IMAGE_FEATURES = " \
    allow-empty-password \
    empty-root-password \
    allow-root-login \
    post-install-logging \
"
IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_INSTALL += "\
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    ${CORE_IMAGE_BASE_INSTALL} \
    curl \
    iptables \
    tor \
    torsocks \
    arti \
    "

IMAGE_OVERHEAD_FACTOR = "2.5"
