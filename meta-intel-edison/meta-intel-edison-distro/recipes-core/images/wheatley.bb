DESCRIPTION = "A fully functional image to run EDISON"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
LICENSE = "MIT"
IMAGE_INSTALL = " ${ROOTFS_PKGMANAGE_BOOTSTRAP} ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_LINGUAS = " "

INITRD = ""
INITRD_IMAGE = ""

# Do not use legacy nor EFI BIOS
PCBIOS = "0"
# Do not support bootable USB stick
NOISO = "1"
ROOTFS = ""

# This is useless stuff, but necessary for building because
# inheriting bootimg also brings syslinux in..
AUTO_SYSLINUXCFG = "1"
SYSLINUX_ROOT = ""
SYSLINUX_TIMEOUT ?= "10"
SYSLINUX_LABELS ?= "boot install"
LABELS_append = " ${SYSLINUX_LABELS} "


# Specify rootfs image type
IMAGE_FSTYPES = "ext4"

inherit core-image

# This has to be set after including core-image otherwise it's overriden with "1"
# and this cancel creation of the boot hddimg
NOHDD = "0"

inherit bootimg
do_bootimg[depends] += "${PN}:do_rootfs"
		
IMAGE_FEATURES += "package-management ssh-server-openssh tools-debug hwcodecs"
#IMAGE_FEATURES += "x11-base x11-sato"
# Allow passwordless root login and postinst logging
IMAGE_FEATURES += "debug-tweaks"

PREFERRED_PROVIDER_jpeg = "jpeg"
PREFERRED_PROVIDER_jpeg-native = "jpeg-native" 

#############################################################
#							NETWORK 						#
#############################################################
SFTP = " \
	openssh-sftp-server \
"

BLUETOOTH_SUPPORT = " \
	bluez5-dev \
	bluez5-obex \
"

WIFI_SUPPORT = " \
    wpa-supplicant \
    wireless-tools \
    ap-mode-toggle \
    hostapd-daemon \
"

# Wifi Firmware patch for 43340 and its patch utility
# service daemon that listens to rfkill events and trigger FW patch download
FIRMWARE_SUPPORT_WIFI = " \
	bcm43340-fw \
	bluetooth-rfkill-event \
"

# Bluetooth Firmware patch for 43340 and its patch utility
# Wifi driver built as a kernel module
FIRMWARE_SUPPORT_BLUETOOTH = " \
	bcm43340-bt \
	bcm43340-mod \
"

AVAHI = " \
    avahi-daemon \
"

CONNMAN = " \
	connman \
	connman-client \
	connman-tools \
	connman-init-systemd \
"

NETWORK_GROUP = " \
    ${SFTP} \
    ${BLUETOOTH_SUPPORT} \
    ${WIFI_SUPPORT} \
    ${FIRMWARE_SUPPORT_BLUETOOTH} \
    ${FIRMWARE_SUPPORT_WIFI} \
    ${AVAHI} \
    ${CONNMAN} \
"

#############################################################
#						   ESSENTIAL 						#
#############################################################


# INTEL MCU FW
MCU_FW = " \
	mcu-fw-load \
	mcu-fw-bin \
"

KERNEL_MODULES = " \
	kernel-modules \
"

CORE_BOOT = " \
	packagegroup-core-boot \
	task-core-boot \
"

# Those are necessary to manually create partitions and file systems on the eMMC
PARTITIONS =  " \
	parted \
	e2fsprogs-e2fsck \
	e2fsprogs-mke2fs \
	e2fsprogs-tune2fs \
	e2fsprogs-badblocks \ 
	libcomerr \
	libss \
	libe2p \
	libext2fs \
	dosfstools \
"

# Clean corrupted journald entries
CLEAN_JOURNAL = " \
	cleanjournal \
"

# Allow passwordless root login and postinst logging
# Provides strace and gdb
DEBUG_TWEAKS = " \
	crashlog \
"

FIRMWARE_SUPPORT = " \
    linux-firmware \
"

MISCELLANEOUS = " \
	watchdog-sample \
	pwr-button-handler \
	blink-led \
	post-install \
	resize-rootfs \
	systemd-analyze \
"

ESSENTIAL_GROUP = " \
	${MCU_FW} \
	${KERNEL_MODULES} \
	${CORE_BOOT} \
	${PARTITIONS} \
	${CLEAN_JOURNAL} \
	${DEBUG_TWEAKS} \
	${FIRMWARE_SUPPORT} \
	${MISCELLANEOUS} \
"

#############################################################
#						  LIBS_EDISON 						#
#############################################################


# MRAA
MRAA = " \
	mraa-dev \
	mraa-doc \
"

# UPM
UPM = " \
	upm-dev \
"

# Edison Arduino stuff
EDISON_ARDUINO = " \
	clloader \
"

# Edison Middleware stuff
EDISON_MIDDLEWARE = " \
	packagegroup-core-buildessential \
	iotkit-opkg \
	zeromq-dev \
	cppzmq-dev \
	paho-mqtt-dev \
	mdns-dev \
	iotkit-comm-js \
	iotkit-comm-c-dev \
	iotkit-agent \
	iotkit-lib-c-dev \
	xdk-daemon \
	oobe \
"

LIBS_EDISON_GROUP = " \
	${MRAA} \
	${UPM} \
	${EDISON_MIDDLEWARE} \
	${EDISON_ARDUINO} \
"

#############################################################
#							AUDIO   						#
#############################################################

# Add audio firmware, ALSA lib and utilities
ALSA = " \
	sst-fw-bin \
	alsa-lib \
	alsa-utils-alsamixer \
	alsa-utils-alsactl \
	alsa-utils-aplay \
	alsa-utils-amixer \
"

# Add pulseaudio
PULSEAUDIO = " \
	pulseaudio-server \
	libpulsecore \
	libpulsecommon \
	libpulse \
	libpulse-simple \
	pulseaudio-misc \
	pulseaudio-service\
"

AUDIO_GROUP = " \
	${PULSEAUDIO} \
	${ALSA} \
"

#############################################################
#							 VIDEO  						#
#############################################################

#Add Gstreamer
GSTREAMER = " \
	gstreamer1.0 \
	gstreamer1.0-meta-base \
	gstreamer1.0-meta-audio \
    gst-plugins-good \
    gstreamer \
"

CAMERA_MODULE = " \
    opencv \
    opencv-samples \
    python-numpy \
"

VIDEO_GROUP = " \
	${GSTREAMER} \
	${CAMERA_MODULE} \
"

#############################################################
#						      TOOLS 	   					#
#############################################################

CONSOLE_TOOLS = " \
    usbutils \
    less \
    unzip \
    diffutils \
    file \
"

EDITORS = " \
    nano \
    vim \
    zsh \
"

#console-tools
EXTRA_TOOLS_INSTALL = " \
    bzip2 \
    devmem2 \
    ethtool \
    findutils \
    iperf \
    htop \
    procps \
    sysfsutils \
    zip \
    media-ctl \
    nodelet \
    class-loader \
    message-runtime \
"

# Adds various other tools
TOOLS = " \
	tcpdump \
	net-tools \
	lsof \
	iperf \
	usbutils \
	i2c-tools \
	wget \
"

MAKE_TOOLS = " \
    make \
    pkgconfig \
    python-json \
    json-glib \
    json-c \
    gnupg \
    gpgme \
    cmake \
    cmake-modules \
    binutils \
    coreutils \
"

# nfs
NFS = " \
	nfs-utils \
"

UPDATE_TOOLS=" \
    subversion \
    git \
    git-perltools \
    ca-certificates \
    apt \
    curl \
    wget \
    tzdata \
"

SHARED_LIBRARY = " \
	ldd \
"

TOOLS_GROUP = " \
	${EDITORS} \
	${TOOLS} \
	${EXTRA_TOOLS_INSTALL} \
	${CONSOLE_TOOLS} \
	${MAKE_TOOLS} \
	${NFS} \
	${UPDATE_TOOLS} \
	${SHARED_LIBRARY} \
"

#############################################################
#							  PYHON 						#
#############################################################

# Python and some basic modules
PYTHON = " \
	python \
	python-dbus \
	python-smartpm \
	python-pygobject \
	python-argparse \
	python-distutils \
	python-pkgutil \
	python-audio \
	python-image \
	python-imaging \
	python-email \
	python-netserver \
	python-xmlrpc \
	python-ctypes \
	python-html \
	python-json \
	python-compile \
	python-misc \
	python-numbers \
	python-unittest \
	python-pydoc \
"

PYTHON_ROS =" \
    python-core \
    python-rosinstall \
    python-rosdep \
    python-wstool \
    python-distutils \
    python-rospkg \
    python-catkin-pkg \
    python-nose \
    python-pyyaml \
    python-vcstools \
    python-resource \
    python-docutils \
    python-gst \
    python-dev \
    tbb \
    roscpp-traits \
    python-numpy \
    python-numeric \
    python-empy \
"

PYTHON_GROUP = " \
	${PYTHON} \
	${PYTHON_ROS} \
"

#############################################################
#							LIBRARY 						#
#############################################################


# Allows to enable OpenMP feature
OPENMP = " \
	libgomp \
"

FORTRAN_TOOLS = " \
    gfortran \
    gfortran-symlinks \
    libgfortran \
    libgfortran-dev \
"

DEV_SDK_INSTALL = " \
    cpp \
    cpp-symlinks \
    gcc \
    gcc-symlinks \
    g++ \
    g++-symlinks \
    libstdc++ \
    libstdc++-dev \
"

LIBRARY_PACKAGES = " \
    libc6-dev \
    libc-dev \
    libstdc++-dev \
    libbz2 \
    ldd \
    libtool \
"

LIBRARY_GROUP = " \
	${OPENMP} \
	${DEV_SDK_INSTALL} \
	${LIBRARY_PACKAGES} \
"
#############################################################
#							ROS      						#
#############################################################

ROS_GROUP =" \
    packagegroup-ros-edison \
	packagegroup-ros-comm \
"

#############################################################
#							NOPE    						#
#############################################################

# SWIG
SWIG = " \
	swig \
"

# mosquitto and dependencies
MOSQUITO = " \
	mosquitto-dev \
	mosquitto-clients \
"

# node and sub-components
NODEJS = " \
	nodejs-dev \
	nodejs-npm \
"

NOPE_GROUP = " \
	${MOSQUITO}	\
	${NODEJS} \
	${SWIG} \
	ethtool \
"

#python-pip

#IMAGE_ROOTFS_SIZE = "524288"
IMAGE_ROOTFS_SIZE = "3489792"

IMAGE_INSTALL += " \
    ${ROS_GROUP} \
    ${LIBRARY_GROUP} \
    ${PYTHON_GROUP} \
    ${TOOLS_GROUP} \
    ${ESSENTIAL_GROUP} \
    ${LIBS_EDISON_GROUP} \
    ${NETWORK_GROUP} \
    ${AUDIO_GROUP} \
    ${VIDEO_GROUP} \
    ${DEV_SDK_INSTALL} \
"

