
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static EntityManagerFactory emf;
    public static EntityManager em;
    public static void main (String[] args) {
        emf = Persistence.createEntityManagerFactory("Menu");
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            try {
                Menu meal1 = new Menu("Potato", 2, 0.1, 0);
                Menu meal2 = new Menu("Soup", 10, 0.2,0);
                Menu meal3 = new Menu("Soup yesterday",9,0.2,1);
                Menu meal4 = new Menu("Pizza Margherita",20,0.9,0);
                Menu meal5 = new Menu("Pizza Margherita M",15,0.6,0);

                em.persist(meal1);
                em.persist(meal2);
                em.persist(meal3);
                em.persist(meal4);
                em.persist(meal5);
                em.getTransaction().commit();
                check();
            } catch (Exception e) {
                e.printStackTrace();
                em.getTransaction().rollback();
            }
        } finally {
            emf.close();
            em.close();
        }
    }
    public static void check() {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to our restaurant");
        while (true) {
            System.out.println("Choose your option:");
            System.out.println("1. View menu");
            System.out.println("2. View discount meals only");
            System.out.println("3. Choose random meals with total weight under 1Kg");
            System.out.println("4. Choose meals with price criteria from-to");
            String choose = in.nextLine();
            switch (choose) {
                case "1" : menu();
                break;
                case "2" : discount();
                break;
                case "3" : under();
                break;
                case "4" : price();
                break;
                default: return;
            }
        }
    }
    public static void price() {
        System.out.println("Type price to search from:");
        Scanner in = new Scanner(System.in);
        String f = in.nextLine();
        System.out.println("Type price to search to:");
        String t = in.nextLine();
        Double from = Double.valueOf(f);
        Double to = Double.valueOf(t);

        Query query = em.createNamedQuery("menu.price", Menu.class);
        query.setParameter("from",from);
        query.setParameter("to",to);
        List<Menu> list = query.getResultList();

        for (Menu menu: list) System.out.println("\u001B[32m" + menu + "\u001B[0m");
    }
    public static void under () {
        Query query = em.createNamedQuery("menu.weight", Menu.class);
        List<Menu> list = query.getResultList();
        List<Menu> out = new ArrayList<>();
        double sum = 0;
        boolean r = true;
        while (r) {
            int random = (int)(Math.random() * list.size());
            if (sum + list.get(random).getWeight() <= 1.0) {
                sum += list.get(random).getWeight();
                out.add(list.get(random));
                list.remove(random);
            } else {
                r = false;
            }
        }
        for (Menu menu:out) System.out.println("\u001B[32m" + menu + "\u001B[0m");
        System.out.println("\u001B[32m" + "Sum of weight " + Math.round(sum * 100.0)/100.0 + "Kg" + "\u001B[0m");
    }
    public static void discount(){
            Query query = em.createNamedQuery("menu.discount",Menu.class);
            List<Menu> list = query.getResultList();
            if (list.size() == 0) System.out.println("\u001B[32m" + "There is no meals with discount today" + "\u001B[0m");
            for(Menu menu:list) System.out.println("\u001B[32m" + menu + "\u001B[0m");
    }
    public static void menu(){
            Query query = em.createNamedQuery("menu.findAll",Menu.class);
            List<Menu> list = query.getResultList();
            for (Menu menu: list) System.out.println("\u001B[32m" + menu + "\u001B[0m");
    }
}
