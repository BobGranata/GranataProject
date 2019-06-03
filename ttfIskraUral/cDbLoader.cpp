#include "cDbLoader.h"
#include <windows.h>

#include <fstream>
#include <regex>

cDbLoader::cDbLoader() : m_vectorFirms(new vector<cFirmModel>)
{
}

cDbLoader::~cDbLoader()
{

}

std::vector<std::string> split(const string& input, const string& regex) {    
    std::regex re(regex);
    std::sregex_token_iterator
        first{input.begin(), input.end(), re, -1},
        last;
    return {first, last};
}

void cDbLoader::initDb(string dbPath)
{
    initFileNamesDbTable(dbPath);

    for (auto itPath = m_vPathDbTable.cbegin(); itPath != m_vPathDbTable.cend(); itPath++)
    {
        ifstream fTable(dbPath + "\\" + itPath->c_str());
        cFirmModel firmModel(*itPath);
        while(fTable)
        {
            string row;
            std::getline(fTable, row);
            vector<std::string> vSplitRow = split(row, ",");
            if (vSplitRow.size() >= 4)
            {
                string fruitName = vSplitRow[0];
                string fruitMaxPrice = vSplitRow[1];
                string fruitProvider = vSplitRow[2];
                string fruitAmount = vSplitRow[3];                
                cRequirementsModel *req = new cRequirementsModel(fruitName, fruitMaxPrice, fruitProvider, fruitAmount);
                firmModel.addRequirement(req);

            }
        }
        m_vectorFirms->push_back(firmModel);
        fTable.close();
    }
}

bool cDbLoader::initFileNamesDbTable(string folder)
{
    m_vPathDbTable.clear();

    const char* str = folder.c_str();
    wstring wstrFolder (str, str+strlen(str));
    wstring search_path = wstrFolder + L"/*.*";
    WIN32_FIND_DATA fd;
    HANDLE hFind = ::FindFirstFile(search_path.c_str(), &fd);
    if(hFind != INVALID_HANDLE_VALUE) {
        do {
            if(! (fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) ) {
                wstring wsFileName = fd.cFileName;
                 const std::string sFN( wsFileName.begin(), wsFileName.end() );
//                m_vPathDbTable.push_back(folder + "\\" + sFN);
                 m_vPathDbTable.push_back(sFN);
            }
        }while(::FindNextFile(hFind, &fd));
        ::FindClose(hFind);

        return true;
    } else {
        return false;
    }
}

vector<cFirmModel> *cDbLoader::vectorFirms() const
{
    return m_vectorFirms.get();
}
