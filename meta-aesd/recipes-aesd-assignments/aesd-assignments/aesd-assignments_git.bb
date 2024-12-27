# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-mukesh2006;protocol=ssh;branch=master"
#SRC_URI = "file://home/mj/Downloads/mj-personal/git/assignments/assignment-3"

PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
#SRCREV = "5889a9ec930f46813bd7e0c79cc3ad07e5901fd5"
#SRCREV = "41711cd0ef89357781c1ae657b4df7cc15e3ff77"
#SRCREV = "4a6fb6481baed1db2340e6c72b78c059b323c01d"
#SRCREV = "ea415b4229d314c43229df72201d79807b3cbad2"
SRCREV = "4425f1e1b2c6cf2a746e4dbdf103dc4e8673ec1b"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

# TODO: Add the aesdsocket application and any other files you need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/aesdsocket"

# TODO: customize these as necessary for any libraries you need for your application
# (and remove comment)
TARGET_LDFLAGS += "-pthread -lrt"
TARGET_CC_ARCH += "${LDFLAGS}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
 # TODO: Install your binaries/scripts here.
 # Be sure to install the target directory with install -d first
 # Yocto variables ${D} and ${S} are useful here, which you can read about at 
 # https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
 # and
 # https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
 # See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb

    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d
}
