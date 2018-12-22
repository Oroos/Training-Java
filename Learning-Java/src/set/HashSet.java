package set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class HashSet
{
    public static void main(final String[] args)
    {
        // a Set cant have a duplicate item

        final String[] things = {"lol", "fjlas", "loL", "jfklsjal", "lol"};
        final List<String> thingsList = new ArrayList<>(Arrays.asList(things));

        System.out.println(thingsList);
        System.out.println();

        final Set<String> set = new HashSet<String>(thingsList); // wieso ist Set kein Interface von Hashset
        System.out.println(set);
        System.out.println();
    }
}
