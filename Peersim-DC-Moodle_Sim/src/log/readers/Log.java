package log.readers;

import java.util.Iterator;

import log.formats.Operation;

public interface Log<T extends Enum<T>> extends Iterator<Operation<T>> {

}
