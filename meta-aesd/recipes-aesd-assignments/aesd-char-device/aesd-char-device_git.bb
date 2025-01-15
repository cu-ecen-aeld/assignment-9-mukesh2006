# Yocto Recipe for AESD Character Driver and Init Scripts
# This recipe installs the AESD character driver, its associated load/unload binaries,
# and the corresponding init script.

# License and License File Checksum
# WARNING: The following LICENSE and LIC_FILES_CHKSUM values are estimates and may require verification.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Source URI and Version Information
# Define the Git repository and commit hash for the source code.
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-mukesh2006;protocol=ssh;branch=master"
SRCREV = "073468f34cbf668614576ea50e9e1d7b6e73cad8" 

# Add the start-stop init script for the driver
SRC_URI += "file://aesd-char-start-stop"

# Versioning based on Git revision
PV = "1.0+git${SRCPV}"

# Define the source directory for the AESD character driver
S = "${WORKDIR}/git/aesd-char-driver"

# Inherit the 'module' class to handle kernel module building
inherit module

# Add kernel build instructions to EXTRA_OEMAKE
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

# Inherit 'update-rc.d' to manage the init script for the driver
inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesd-char-start-stop"

# Include the init script, load/unload binaries in the package
FILES:${PN} += "${sysconfdir}/init.d/aesd-char-start-stop"
FILES:${PN} += "${bindir}/aesdchar_load"
FILES:${PN} += "${bindir}/aesdchar_unload"

# Installation Instructions for the Driver and Scripts
do_install() {
    # Install the init script for the driver in the init.d directory
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/aesd-char-start-stop ${D}${sysconfdir}/init.d

    # Install the load and unload binaries to the appropriate directory
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdchar_load ${D}${bindir}
    install -m 0755 ${S}/aesdchar_unload ${D}${bindir}

    # Install the kernel module into the appropriate directory
    install -d ${D}/lib/modules/${KERNEL_VERSION}
    install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}
}

# Notes:
# - Ensure that the kernel module and init script have been properly tested.
# - Verify that the license file checksum is correct before releasing this recipe.
# - The recipe assumes that the source repository and commit hash are valid.