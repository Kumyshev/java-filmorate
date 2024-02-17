package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class my {
    public static void main(String[] args) {
        
        akhma a1 = new akhma();
        a1.list.add(1);
        a1.list.add(1);
        a1.list.add(1);
        a1.list.add(1);

        akhma a2 = new akhma();
        a2.list.add(1);
        a2.list.add(1);
        a2.list.add(1);

        akhma a3 = new akhma();

        a3.list.add(1);
        a3.list.add(1);
        a3.list.add(1);
        a3.list.add(1);
        a3.list.add(1);

        List<akhma> bert = new ArrayList<>();

        bert.add(a3);
        bert.add(a2);
        bert.add(a1);

        var list = bert.stream()
        .sorted(Comparator.comparingInt(o->((akhma) o).getList().size()).reversed())
                .limit(12)
                .collect(Collectors.toList());

       System.err.println(list);
            
    }


    /**
     * Innermy
     */
    static class akhma {
    
        private List<Integer> list = new ArrayList<>();

        public List<Integer> getList() {
            return list;
        }
    }
}
