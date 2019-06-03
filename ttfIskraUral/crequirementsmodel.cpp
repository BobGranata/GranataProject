#include "cRequirementsModel.h"

cRequirementsModel::cRequirementsModel()
{

}

cRequirementsModel::cRequirementsModel(string fruitName, string fruitMaxPrice, string fruitProvider, string fruitAmount)
                                           : m_fruitName(fruitName), m_fruitMaxPrice(fruitMaxPrice), m_fruitProducer(fruitProvider), m_fruitAmount(fruitAmount)
{
}

string cRequirementsModel::fruitName() const
{
    return m_fruitName;
}

string cRequirementsModel::fruitMaxPrice() const
{
    return m_fruitMaxPrice;
}

string cRequirementsModel::fruitProducer() const
{
    return m_fruitProducer;
}

string cRequirementsModel::fruitAmount() const
{
    return m_fruitAmount;
}
