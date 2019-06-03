#ifndef IDBLOADER_H
#define IDBLOADER_H

#include <iostream>
#include <vector>
#include <cfirmmodel.h>

using namespace std;

struct iDbLoader
{
    virtual ~iDbLoader() {}
    virtual void initDb(string dbPath) = 0;
    virtual vector<cFirmModel> *vectorFirms() const = 0;
};

#endif // IDBLOADER_H
