SUMMARY = "Running /etc/init.d/S98lddmodules stop should unload all modules on the buildroot instance"
DESCRIPTION = "This recipe copies a custom script to the root filesystem."

LICENSE = "CLOSED"

SRC_URI = "file://S98lddmodules"
SRCREV = "1"

FILES:${PN} += "${sysconfdir}/init.d/S98lddmodules"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98lddmodules"

do_install() {

    install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/S98lddmodules ${D}${sysconfdir}/init.d/
}