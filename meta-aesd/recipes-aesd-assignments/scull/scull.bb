
DESCRIPTION = "Scull Linux Device Driver"

LICENSE = "CLOSED"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-mukesh2006.git;protocol=https;branch=main;dir=scull"

PV = "1.0+git${SRCPV}"
SRCREV="0d79b79040d239f83b842350dc2d54c34d0d074e"

S = "${WORKDIR}/git/scull"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-scull"

do_install:append () {
    # Install your binaries/scripts here.
    # Be sure to install the target directory with install -d first
    # Yocto variables ${D} and ${S} are useful here, which you can read about at 
    # https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
    # and
    # https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
    # See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
    install -d ${D}${bindir}
    install -m 0755 ${S}/scull_load ${D}${bindir}/
    install -m 0755 ${S}/scull_unload ${D}${bindir}/

    install -m 0755 ${WORKDIR}/git/misc-modules/module_load ${D}${bindir}/
    install -m 0755 ${WORKDIR}/git/misc-modules/module_unload ${D}${bindir}/
}

FILES:${PN} += "${bindir}"
FILES:${PN} += "${bindir}/scull_load"
FILES:${PN} += "${bindir}/scull_unload"