# meta-tor

Yocto/Openembedded layer for Tor Onion Router, providing recipes to build and integrate:

<p align="center"><img src="tor.png" width="200" height="116" /></p>

* Tor (Core C implementation)
* Arti (Core Rust implementation)
* Torsocks

These tools enable anonymous networking, hidden services, and secure remote access for embedded systems and IoT devices.

## Build

This layer relies on OpenEmbedded/Yocto build system and depends on:

```
[OECORE]
URI: https://git.yoctoproject.org/git/poky.git
layers: meta
branch: same dedicated branch as meta-tor
```
and

```
[OE]
URI: https://github.com/openembedded/meta-openembedded.git
layers: meta-oe
branch: same dedicated branch as meta-tor
```

It can be added to your layer(s) and enabling `tor` by adding:

```
IMAGE_INSTALL:append = " tor"
```

or built standalone using [kas-tool](https://github.com/siemens/kas):

```
KAS_MACHINE=qemux86-64 kas build kas-tor.yml
```

or using kas docker container:

```
KAS_MACHINE=qemux86-64 kas-container build kas-tor.yml
```

## Run in Qemu Emulator

```
KAS_MACHINE=qemux86-64 kas shell kas-tor.yml -c 'runqemu kvm serialstdio nographic qemuparams="-m 1024"'
```

## Hidden Services & Remote Access

Tor supports onion hidden services, which allow inbound connections even behind NAT.

To configure, add your TCP Service Port (e.g., 1234) to [/etc/tor/torrc](recipes-tor/tor/tor/torrc):

```
HiddenServicePort 1234 127.0.0.1:1234
```

The Onion address is generated at `/var/tor/hidden_service/hostname`:

```
cat /var/tor/hidden_service/hostname 
ljssl7tsxv4vcucifya7hekupgrytezbyz56lyar6pice672icaou4yd.onion
```

Now it's possible to access your hidden service by using remotely `torsocks` for example:

```
torsocks ssh root@ljssl7tsxv4vcucifya7hekupgrytezbyz56lyar6pice672icaou4yd.onion
```
or

```
torsocks wget http://ljssl7tsxv4vcucifya7hekupgrytezbyz56lyar6pice672icaou4yd.onion
```
For http/https traffic you can also use direclty [tor-browser](https://www.torproject.org/download)


> **Note:** Ensure that your TCP service is using TLS since the Tor exit node can inspect the traffic if not encrypted.

## Tor Relay

To run as a Tor relay the [torrc.relay](recipes-tor/tor/tor/torrc.relay) config can be used.

> **Note:** If using Tor relay config the Ip Address is published so better not run in parallel with tor client.