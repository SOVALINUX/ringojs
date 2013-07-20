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

public class JsModuleLoader extends ModuleLoader {

    public JsModuleLoader() {
        super(".js");
    }

    @Override
    public Object load(final Context cx,  RhinoEngine engine, final Object securityDomain,
                       String moduleName, String charset, final Resource resource)
            throws Exception {
        return AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    return cx.compileReader(resource.getReader(),
                            resource.getRelativePath(),
                            resource.getLineNumber(), securityDomain);
                } catch (IOException iox) {
                    throw new RuntimeException(iox);
                }
            }
        });
    }
}