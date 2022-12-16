package io.github.davidchild.bitter.parse;

import org.junit.Test;

import io.github.davidchild.bitter.entity.TUserInfo;

public class BitterPredicateTest {
    @Test
    public void TestPredicate() {
        TUserInfo busReturnVisitInfo = new TUserInfo();
        busReturnVisitInfo.setUsername("davidChild");
        BitterPredicate<TUserInfo> predicate_t0 = p -> p.getUsername().equals("hjb");
        BitterVisitor visitor = new BitterVisitor("`", "`");
        System.out.println(predicate_t0.sql(visitor) + System.lineSeparator());
        visitor.clear();

        // test like 'd%' and like '%d'
        BitterPredicate<TUserInfo> predicate_t1 = p -> p.getUsername().startsWith("d") || p.getUsername().endsWith("d");
        System.out.println(predicate_t1.sql(visitor) + System.lineSeparator());
        visitor.clear();

        // test `userName` like concat('%',?,'%')
        BitterPredicate<TUserInfo> predicate_t2 = p -> p.getUsername().contains("david");
        System.out.println(predicate_t2.sql(visitor) + System.lineSeparator());
        visitor.clear();

        BitterPredicate<TUserInfo> predicate_t3 =
            p -> ((p.getPhone().equals("123") && p.getUsername() == "123" && p.getUserAge() > 18)
                || (p.getUsername() == "jb")) || (p.getUserAge() < 36);

        System.out.println(predicate_t3.sql(visitor) + System.lineSeparator());
        visitor.clear();
    }

    @Test
    public void TestPredicate_2() {
        TUserInfo busReturnVisitInfo = new TUserInfo();
        busReturnVisitInfo.setUsername("davidChild");
        BitterVisitor visitor = new BitterVisitor("`", "`");
        BitterPredicate<TUserInfo> predicate_t3 = p -> p.getUserAge() > 18;
        System.out.println(predicate_t3.sql(visitor) + System.lineSeparator());
        visitor.clear();
    }

    @Test
    public void TestPredicate_3() {
        TUserInfo busReturnVisitInfo = new TUserInfo();
        busReturnVisitInfo.setUsername("davidChild");
        BitterVisitor visitor = new BitterVisitor("`", "`");
        BitterPredicate<TUserInfo> predicate_t3 = p -> (p.getUserAge() > 18) || (p.getUserAge() < 14);
        System.out.println(predicate_t3.sql(visitor) + System.lineSeparator());
        visitor.clear();
    }

    @Test
    public void TestPredicate_4() {
        TUserInfo busReturnVisitInfo = new TUserInfo();
        busReturnVisitInfo.setUsername("davidChild");
        BitterVisitor visitor = new BitterVisitor("`", "`");
        BitterPredicate<TUserInfo> predicate_t3 =
            p -> ((p.getUserAge() > 18) || (p.getUsername() == "jb")) || (p.getUserAge() < 36);
        System.out.println(predicate_t3.sql(visitor) + System.lineSeparator());
        visitor.clear();
    }
}
