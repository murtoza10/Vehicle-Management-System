package sample;

import edu.sust.db.OwnerDetails;
import edu.sust.db.Vehicle;
import org.hibernate.*;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

/**
 * Created by Murtoza10 on 11/3/2014.
 */
public class VehicleQuery {
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;

    public String FindChasisNum(){
        String chasis=null;
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Vehicle v INNER JOIN v.owner AS o WHERE o.ownerId = :ownid").setParameter("ownid",12);


            List<Object[]> list = query.list();

            for (Object object : list) {
                Object[] li = (Object[])object;
                for(Object liItem:li){

                    if (liItem instanceof Vehicle) {
                         chasis=((Vehicle) liItem).getChasisNum();
                        //System.out.println(((Vehicle) liItem).getChasisNum());


                    }else{
                        System.out.println(((OwnerDetails)liItem).getName());

                    }

                }
            }
            tx.commit();
        }
        catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    return chasis;
    }

}
