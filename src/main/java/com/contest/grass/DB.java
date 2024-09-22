//package com.contest.grass;
//
//import com.contest.grass.entity.*;
//import com.contest.grass.entity.item.eco.Getitem;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//
//@Component
//@RequiredArgsConstructor
//public class DB {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit1();
//        initService.dbInit2();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//
//        public void dbInit1() {
//            System.out.println("Init1" + this.getClass());
//            User user = createUser("dongyeop3@naver.com","dodo1245214","121345679","userDD", "강원도", "13", "11111");
//            em.persist(user);
//
//            Getitem Soap1 = createBasket("비누5", "10,000", 100);
//            em.persist(Soap1);
//
//            Getitem Soap2 = createBasket("비누6", "20,000", 200);
//            em.persist(Soap2);
//
//            OrderItem orderItem1 = OrderItem.createOrderItem(Soap1, 10000, 1);
//            OrderItem orderItem2 = OrderItem.createOrderItem(Soap2, 20000, 2);
//
//            Delivery delivery = createDelivery(user);
//            Orders orders = Orders.createOrder(user, delivery, orderItem1, orderItem2);
//            em.persist(orders);
//        }
//
//        public void dbInit2() {
//            User user = createUser("dongyeop4@naver.com","dodo3245214","121345678","userDC", "경기도", "24", "22222");
//            em.persist(user);
//
//            Getitem basket1 = createBasket("가방6", "20,000", 200);
//            em.persist(basket1);
//
//            Getitem basket2 = createBasket("가방7", "40,000", 300);
//            em.persist(basket2);
//
//            OrderItem orderItem1 = OrderItem.createOrderItem(basket1, 20000, 3);
//            OrderItem orderItem2 = OrderItem.createOrderItem(basket2, 40000, 4);
//
//            Delivery delivery = createDelivery(user);
//            Orders orders = Orders.createOrder(user, delivery, orderItem1, orderItem2);
//            em.persist(orders);
//        }
//
//        private User createUser(String email, String nickname,String password, String name, String city, String street, String zipcode) {
//            User user = new User();
//            user.setEmail(email);
//            user.setNickname(nickname);
//            user.setPassword(password);
//            user.setName(name);
//            user.setAddress(new Address(city, street, zipcode));
//            return user;
//        }
//
//        private Getitem createBasket(String name, String price, int Qty) {
//            Getitem Soap = new Getitem();
//            Soap.setName(name);
//            Soap.setPrice(price);
//            Soap.setQty(Qty);
//            return Soap;
//        }
//
//        private Delivery createDelivery(User user) {
//            Delivery delivery = new Delivery();
//            delivery.setAddress(user.getAddress());
//            return delivery;
//        }
//    }
//}
//
