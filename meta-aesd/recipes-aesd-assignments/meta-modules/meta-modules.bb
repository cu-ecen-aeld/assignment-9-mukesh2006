DESCRIPTION = "Meta Modules"

LICENSE = "CLOSED"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-mukesh2006.git;protocol=https;branch=main;dir=misc-modules"

PV = "1.0+git${SRCPV}"
SRCREV = "0d79b79040d239f83b842350dc2d54c34d0d074e"

S = "${WORKDIR}/git/misc-modules"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-faulty"
RPROVIDES:${PN} += "kernel-module-hello"