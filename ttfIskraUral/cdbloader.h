#ifndef CDBLOADER_H
#define CDBLOADER_H

#include <memory>

#include "cFirmModel.h"
#include "iDbLoader.h"

class cDbLoader : public iDbLoader
{
private:
    bool initFileNamesDbTable(string folder);

    vector<string> m_vPathDbTable;    
    shared_ptr<vector<cFirmModel>> m_vectorFirms;
public:
    cDbLoader();
    ~cDbLoader();

    void initDb(string dbPath);    
    vector<cFirmModel> *vectorFirms() const;
};

#endif // CDBLOADER_H
