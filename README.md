# meta-tor

Yocto/Openembedded layer for Tor Onion Router

<p align ="center"><img src=tor.png width=200 height=116 /></p>


## Build 

```
KAS_MACHINE=qemux86-64 kas build kas-tor.yml
```

## Run in Qemu Emulator

```
KAS_MACHINE=qemux86-64 kas shell kas-tor.yml -c 'runqemu kvm serialstdio nographic qemuparams="-m 1024"'
```
