package org.esiea.catrisse_turrata.ubeer;

/**
 * Created by gustu on 23/03/2018.
 */



public class Bar {
    String name;
    String address;
    String isOpen;
    String rank;
    //boolean isOPen;
    //float rank;

    Bar(String name, String address, String isOpen, String rank)
    {
        this.name=name;
        this.address=address;
        this.isOpen=isOpen;
        this.rank=rank;

    }


    public String getName()
    {
        return this.name;
    }



    public String getAdress()
    {
        return this.getAdress();
    }

    public String getIsOpen()
    {
        return this.isOpen;
    }


    public String getRank()
    {
        return this.rank;
    }

    @Override
    public String toString()
    {

        return this.name;
    }


   /* public void setName(String name)
    {
    this.name=name;
    }


    public void setAdress(String name)
    {
        this.adress=adress;
    }*/

}
