/*
 *  Copyright 2012 Hannes Wallnoefer <hannes@helma.at>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.ringojs.engine;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.GeneratedClassLoader;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.SecurityController;
import org.mozilla.javascript.json.JsonParser;
import org.ringojs.repository.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class ScriptedModuleLoader extends ModuleLoader {
    
    Function function;

    public ScriptedModuleLoader(String extension, Function function) {
        super(extension);
        this.function = function;
    }
    
    @Override
    public Object load(Context cx, RhinoEngine engine, Object securityDomain,
                       String moduleName, String charset, Resource resource)
                       throws Exception {
        Scriptable scope = engine.getScope();
        Object[] args = {cx.getWrapFactory().wrap(cx, scope, resource, null)};
        Object source = function.call(cx, scope, scope, args);

        if (source instanceof CharSequence) {
            return cx.compileString(source.toString(), resource.getRelativePath(),
                    resource.getLineNumber(), securityDomain);
        } else if (source instanceof Scriptable) {
            return source;
        } else {
            throw new RuntimeException("Loader must return script or object");
        }
    }
}