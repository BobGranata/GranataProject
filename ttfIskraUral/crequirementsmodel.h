#ifndef CREQUIREMENTSMODEL_H
#define CREQUIREMENTSMODEL_H

#include <iostream>

using namespace std;

class cRequirementsModel
{
private:
    string m_fruitName;
    string m_fruitMaxPrice;
    string m_fruitProducer;
    string m_fruitAmount;
public:
    cRequirementsModel();
    cRequirementsModel(string, string, string, string);
    string fruitName() const;
    string fruitMaxPrice() const;
    string fruitProducer() const;
    string fruitAmount() const;
};

#endif // CREQUIREMENTSMODEL_H
