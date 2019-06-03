#ifndef CFIRMMODEL_H
#define CFIRMMODEL_H

#include <vector>
#include <memory>
#include "cRequirementsModel.h"

class cFirmModel
{
    shared_ptr<vector<cRequirementsModel*>> m_vRequirements;
    string m_firmName;
public:
    cFirmModel(string firmName);

    void addRequirement(cRequirementsModel *requirement);
    vector<cRequirementsModel *> *vRequirements() const;
    string firmName() const;
};

#endif // CFIRMMODEL_H
