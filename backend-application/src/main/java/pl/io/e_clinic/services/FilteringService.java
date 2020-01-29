package pl.io.e_clinic.services;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Chainowalny filter, w którym argumenty mogą być nullami.
 *
 * Przykładowe użycie (patrz PatientsController):
 *
 * List<User> patients = users.getContent();
 *
 * //opcjonalne filtrowanie
 * patients = new FilteringService<>(patients)
 *         .contains(firstName, User::getFirstName)
 *         .contains(lastName, User::getLastName)
 *         .contains(contactNumber, User::getContactNumber)
 *         .contains(userId, User::getUserId)
 *         .getFiltered();
 */
public class FilteringService<T> {

    private List<T> filteredList;

    public FilteringService(List<T> input) {
        this.filteredList = input;
    }

    public List<T> getFiltered() {
        return filteredList;
    }

    /**
     * Filtruje według Longa match.
     */
    public FilteringService<T> contains(Long match, LongGetter<T> getter) {
        if (match == null) return this;

        filteredList = filteredList
                .stream()
                .filter(
                        (T t) -> {
                            return ("" + getter.getLong(t)).contains("" + match);
                        })
                .collect(Collectors.toList());
        return this;
    }

    /**
     * Filtruje według stringa match. Ignoruje case.
     */
    public FilteringService<T> contains(String match, StringGetter<T> getter) {
        if (match == null) return this;

        String matchLowerCase = match.toLowerCase();

        filteredList = filteredList
                .stream()
                .filter(
                        (T t) -> {
                            return getter
                                    .getString(t)
                                    .toLowerCase()
                                    .contains(matchLowerCase);
                        })
                .collect(Collectors.toList());
        return this;


    }

    /**
     * Filtruje według daty match. Daty muszą być równe co do dnia.
     */
    public FilteringService<T> contains(Date match, DateGetter<T> getter) {
        if (match == null) return this;

        filteredList = filteredList
                .stream()
                .filter(
                        (T t) -> {
                            return DateService.isDayEqual(getter.getDate(t), match);
                        })
                .collect(Collectors.toList());
        return this;

    }

    @FunctionalInterface
    public interface StringGetter<T> {
        String getString(T obj);
    }

    @FunctionalInterface
    public interface LongGetter<T> {
        Long getLong(T obj);
    }

    @FunctionalInterface
    public interface DateGetter<T> {
        Date getDate(T obj);
    }

}
