DESCRIPTION = "Tor Image"

inherit core-image

EXTRA_IMAGE_FEATURES = "debug-tweaks"
IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_INSTALL += "\
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    ${CORE_IMAGE_BASE_INSTALL} \
    curl \
    nginx \
    iptables \
    tor \
    torsocks \
    arti \
    "
