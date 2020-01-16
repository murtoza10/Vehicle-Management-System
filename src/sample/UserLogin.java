package sample;

import edu.sust.db.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.hibernate.*;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import java.net.URL;
import java.security.acl.Owner;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Murtoza10 on 8/27/2014.
 */
public class UserLogin implements Initializable,
        ControlledScreen {

    ScreensController myController;
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    boolean ub;
    @FXML
    TextField txtuser;
    @FXML
    PasswordField txtpass;
    @FXML
    Label errorlabel;



    @FXML
    Button show;

    @FXML
    ToggleButton tog;

    public static int rank;
    public static int uid;
    public static  int user3;
    public static  String s;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //SettingLabelUser3();


    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }
    @FXML
    private void goToUser(ActionEvent event){
            UserLoginQuery ULQ =new UserLoginQuery();
            User user= (User) ULQ.FindUserByUsername(txtuser.getText().toString());
                    //(User)session.get(User.class, txtuser.getText().toString());
            uid=user.getUserId();
            if(user.getPassword().equals(txtpass.getText().toString())){
                rank=user.getRank();
                if(rank==1)
                {
                    myController.setScreen(ScreensFramework.User1_SCREEN);
                    User1 user1=(User1)ScreensFramework.user1;
                    user1.SettingLabelUser1();
                }
                else if(rank==2){
                    myController.setScreen(ScreensFramework.User2_SCREEN);
                    User2 user2=(User2)ScreensFramework.user2;
                    user2.SettingLabelUser2();
                }
                else if(rank==3){
                    user3 = 3;
                    //SettingLabelUser3(uid);

                    myController.setScreen(ScreensFramework.User3_SCREEN);
                    User3 user3=(User3)ScreensFramework.user3;
                    user3.SettingLabelUser3(uid);

                }
            }
            else{
                errorlabel.setText("Password is not correct");
            }
            //System.out.println(rank);



    }

//    @FXML
//public void SettingLabelUser3(){
//
//
//    int own=0,i=1;
//    String phon=null,chasis=null;
//    try {
//        factory = new Configuration().configure().buildSessionFactory();
//    } catch (Throwable ex) {
//        System.err.println("Failed to create sessionFactory object." + ex);
//        throw new ExceptionInInitializerError(ex);
//    }
//    Session session = factory.openSession();
//    Transaction tx = null;
//    try {
//        tx = session.beginTransaction();
//        UserLogin UL=new UserLogin();
//
////            String hql = "from OwnerDetails where user = :userid";
////            List result = session.createQuery(hql)
////                    .setParameter("userid", UL.uid)
////                    .list();
////            System.out.println(UL.uid);
////            OwnerDetails owner =(OwnerDetails)result.get(0);
//        Query query = session.createQuery("FROM OwnerDetails o "
//                + "INNER JOIN o.user AS u where u.userId = :userid").setParameter("userid", UL.uid);
//        List<Object[]> list = query.list();
//
//        for (Object object : list) {
//            Object[] li = (Object[])object;
//            for(Object liItem:li){
//                if (liItem instanceof OwnerDetails) {
//                    own= ((OwnerDetails)liItem).getOwnerId();
//                }
//            }
//        }
//
//        OwnerDetails owner = (OwnerDetails)session.get(OwnerDetails.class, own);
//        Set phones = owner.getPhones();
//        for (Iterator iterator2 =
//                     phones.iterator(); iterator2.hasNext();){
//            Phone phone = (Phone) iterator2.next();
//            System.out.println(phone.getPhone());
//            if(i<2) {
//                phon=(phone.getPhone() + ",");
//                i++;
//            }
//            else {
//                phon += phone.getPhone();
//            }
//        }
////            Criteria criteria = session.createCriteria(OwnerDetails.class);
////            criteria.add(Restrictions.like("user", UL.uid));
//////            criteria.uniqueResult();
////            OwnerDetails owner = (OwnerDetails) criteria.uniqueResult();
//        System.out.println(owner.getName());
//        user3.name.setText(owner.getName());
//        user3.nid.setText(Integer.toString(owner.getNationalId()));
//        user3.ownerid.setText(Integer.toString(owner.getOwnerId()));
//        String pattern = "dd-MM-yyyy";
//        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//        user3.dob.setText(formatter.format(owner.getDob()));
//        user3.curraddrss.setText(owner.getCurrAddrss());
//        user3.permaddrss.setText(owner.getPermAddrss());
//        user3.phn.setText(phon);
//
////            VehicleQuery VQ=new VehicleQuery();
//        Query query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE o.ownerId = :ownid").setParameter("ownid", own);
//
//        List<Object[]> list1 = query1.list();
//
//        for (Object object : list1) {
//            Object[] li = (Object[])object;
//            for(Object liItem:li){
//                if (liItem instanceof Vehicle) {
//                    user3.enginenum.setText(((Vehicle) liItem).getEngineNum());
//                    user3.chasisnum.setText(((Vehicle) liItem).getChasisNum());
//                    user3.color.setText(((Vehicle) liItem).getColor());
//                }else if(liItem instanceof VehicleBuild){
//                    user3.manufacturer.setText(((VehicleBuild) liItem).getManufacturer());
//                    user3.model.setText(((VehicleBuild) liItem).getModel());
//                }else if(liItem instanceof VehicleRegInfo){
//                    user3.regnum.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
//                    user3.platenum.setText(Integer.toString(((VehicleRegInfo) liItem).getPlateNum()));
//                }
//
//            }
//        }
////           Vehicle vehicle= (Vehicle)session.get(Vehicle.class,chasis);
////            System.out.println(vehicle.getChasisNum());
//////            System.out.println(vehicle.getBuild().getBuildId());
////            enginenum.setText(vehicle.getEngineNum());
////            chasisnum.setText(vehicle.getChasisNum());
////            color.setText(vehicle.getColor());
//
//        tx.commit();
//
//    } catch (HibernateException e) {
//        if (tx != null) tx.rollback();
//        e.printStackTrace();
//    } finally {
//        session.close();
//    }
//}
    //any required method here


}
