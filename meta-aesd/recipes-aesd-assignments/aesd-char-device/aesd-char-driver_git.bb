# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "CLOSED"
#LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-mukesh2006;protocol=ssh;branch=master"
## Add the start-stop-script 
SRC_URI += "file://aesd-char-start-stop"


# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "53485fe3eaa7e26ef9b01fd507aef9dcab9366b7"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

# for start
inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesd-char-start-stop"

FILES:${PN} += "${sysconfdir}/init.d/aesd-char-start-stop"

FILES:${PN} += "${bindir}/aesdchar_load"
FILES:${PN} += "${bindir}/aesdchar_unload"

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/aesd-char-start-stop ${D}${sysconfdir}/init.d	
	
        install -d ${D}${bindir}
        install -m 0755 ${S}/aesdchar_load ${D}${bindir}
        install -m 0755 ${S}/aesdchar_unload ${D}${bindir}

	install -d ${D}/lib/modules/${KERNEL_VERSION}
	install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}
}