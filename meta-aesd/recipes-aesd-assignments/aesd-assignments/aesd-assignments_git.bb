# Yocto Recipe for AESD Socket Application and Init Script
# This recipe installs the AESD socket application along with its associated init script.

# License Information:
# Refer to the common Yocto licenses available at https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Source URI: 
# Define the source repository for your assignments. Use SSH for Git access.
# You can uncomment and modify the SRC_URI line to use a local path if needed.
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-mukesh2006;protocol=ssh;branch=master"

# Reference a specific commit hash from your assignments repository (optional)
#SRCREV = "${AUTOREV}"
SRCREV = "ab0cbabe7601fd67c08e802083820ff8d0f0674b"

# Versioning based on Git revision
PV = "1.0+git${SRCPV}"

# Source directory:
# Define the source directory relative to WORKDIR. WORKDIR is set automatically by Yocto.
# We are building from the "server" directory in the assignments repo.
S = "${WORKDIR}/git/server"

# Install the aesdsocket application and any other necessary files
# Ensure to include any additional binaries or files required by your application
FILES:${PN} += "${bindir}/aesdsocket"

# Specify additional flags for linking libraries as needed for your application.
# Customize the flags for your application's dependencies (e.g., pthread, real-time libraries).
TARGET_LDFLAGS += "-pthread -lrt"
TARGET_CC_ARCH += "${LDFLAGS}"

# Initialize the init script for managing the service.
# This manages the startup and shutdown of the aesdsocket application.
inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"

# Define empty configure step since there are no configuration steps in this case
do_configure() {
    # No configuration required for this recipe
    :
}

# Compile the application using the Makefile provided in the source.
do_compile() {
    oe_runmake
}

# Install the binaries, scripts, and configuration files.
do_install() {
    # Install aesdsocket binary to the correct directory
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}

    # Install the init script for service management to init.d directory
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d
}