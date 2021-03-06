/**
 * A wrapper around a singleton id and value.
 */

package org.ringojs.engine;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSGetter;
import org.ringojs.repository.FileResource;
import org.ringojs.repository.Repository;
import org.ringojs.repository.Resource;
import org.ringojs.repository.Trackable;

import java.io.IOException;

public class Singleton {

    final String key;
    boolean evaluated = false;
    Object value = Undefined.instance;

    public Singleton(Trackable source, String id) {
        this.key = source.getPath() + ":" + id;
    }

    public synchronized Object getValue(Function function, Scriptable scope,
                                 ModuleObject obj) {
        if (!evaluated && function != null) {
            Context cx = Context.getCurrentContext();
            value = function.call(cx, scope, obj, ScriptRuntime.emptyArgs);
            evaluated = true; // only if evaluation was successful
        }
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Singleton
                && key.equals(((Singleton) obj).key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}