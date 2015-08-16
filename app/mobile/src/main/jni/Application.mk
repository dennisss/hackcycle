

APP_ABI := armeabi-v7a
#APP_STL := gnustl_static
APP_PLATFORM := android-19


APP_LDFLAGS := -llog -landroid #-lstdc++


APP_STL := gnustl_shared
APP_CPPFLAGS := -frtti -fexceptions -mfloat-abi=softfp -mfpu=neon -std=gnu++0x -Wno-deprecated -fpermissive
#APP_ABI := armeabi
#APP_OPTIM := debug
