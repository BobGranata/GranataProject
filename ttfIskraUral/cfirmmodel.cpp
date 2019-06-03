#include "cFirmModel.h"

cFirmModel::cFirmModel(string firmName) : m_vRequirements(new vector<cRequirementsModel*>), m_firmName(firmName)
{

}

void cFirmModel::addRequirement(cRequirementsModel *requirement)
{
    m_vRequirements->push_back(requirement);
}

vector<cRequirementsModel *> *cFirmModel::vRequirements() const
{
    return m_vRequirements.get();
}

string cFirmModel::firmName() const
{
    return m_firmName;
}
