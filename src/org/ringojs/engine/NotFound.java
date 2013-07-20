/*
 *  Copyright 2008 Hannes Wallnoefer <hannes@helma.at>
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

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.WrapFactory;
import org.ringojs.util.StringUtils;
import org.ringojs.repository.*;
import org.mozilla.javascript.ClassShutter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * This is used as return value in {@link RingoConfig#getResource(String)}
 * and {@link RingoConfig#getRepository(String)} when the given path
 * could not be resolved.
 */
class NotFound extends AbstractResource implements Repository {

    NotFound(String path) {
        this.path = path;
        int slash = path.lastIndexOf('/');
        this.name = slash < 0 ? path : path.substring(slash + 1);
        setBaseNameFromName(name);
    }

    public long getLength() {
        return 0;
    }

    public InputStream getInputStream() throws IOException {
        throw new FileNotFoundException("\"" + path + "\" not found");
    }

    public long lastModified() {
        return 0;
    }

    public boolean exists() {
        return false;
    }

    public Repository getChildRepository(String path) throws IOException {
        return this;
    }

    public Resource getResource(String resourceName) throws IOException {
        return this;
    }

    public Resource[] getResources() throws IOException {
        return new Resource[0];
    }

    public Resource[] getResources(boolean recursive) throws IOException {
        return new Resource[0];
    }

    public Resource[] getResources(String resourcePath, boolean recursive) throws IOException {
        return new Resource[0];
    }

    public Repository[] getRepositories() throws IOException {
        return new Repository[0];
    }

    public void setRoot() {}

    public URL getUrl() throws UnsupportedOperationException, MalformedURLException {
        throw new UnsupportedOperationException("Unable to resolve \"" + path + "\"");
    }

    public String toString() {
        return "Resource \"" + path + "\"";
    }
}
