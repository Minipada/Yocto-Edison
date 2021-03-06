SUMMARY = "discover, browse and search UPNP/DLNA media servers"
HOMEPAGE = "https://01.org/dleyna/"

LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c \
                    file://src/media-service-upnp.c;beginline=1;endline=21;md5=584eb103b4f6f3432c43d169b6a123a1"

DEPENDS = "dbus glib-2.0 gssdp gupnp gupnp-av gupnp-dlna libsoup-2.4"

SRC_URI = "git://github.com/01org/${BPN}.git"
SRCREV = "1996ecbe4a06c95d22f7d958e32e3d28f7a4a2e9"
S = "${WORKDIR}/git"

inherit autotools

do_install_append() {
    install -d ${D}${bindir}
    install -m 0755 dms-info ${D}${bindir}
}

PACKAGES =+ "${PN}-tests"

FILES_${PN} += "${datadir}/dbus-1/services/*.service"
FILES_${PN}-tests = "${bindir}/dms-info"
