package sys.database;

import java.util.HashSet;

public interface Database<T> {
    HashSet<T> getData();
}
