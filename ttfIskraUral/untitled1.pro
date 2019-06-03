TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += \
        main.cpp \
    cFirmModel.cpp \
    cRequirementsModel.cpp \
    cDbQuery.cpp \
    cDbLoader.cpp

HEADERS += \
    cFirmModel.h \
    cRequirementsModel.h \
    cDbQuery.h \
    cDbLoader.h \
    idbquery.h \
    idbloader.h
