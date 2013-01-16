/*
 * Copyright (C) 2012 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.portal.mop.site;

import java.util.ArrayList;

import org.gatein.portal.mop.site.SiteKey;
import org.gatein.portal.mop.site.SiteType;
import org.exoplatform.portal.mop.Utils;
import org.exoplatform.portal.pom.config.POMSession;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.api.workspace.Workspace;

/** @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a> */
abstract class DataCache {

    protected abstract SiteData getSite(POMSession session, SiteKey key);

    protected abstract void removeSite(POMSession session, SiteKey key);

    protected abstract void putSite(SiteData data);

    protected abstract ArrayList<SiteKey> getSites(POMSession session, SiteType key);

    protected abstract void putSites(POMSession session, SiteType key, ArrayList<SiteKey> sites);

    protected abstract void removeSites(POMSession session, SiteType key);

    protected abstract void clear();

    final SiteData getSiteData(POMSession session, SiteKey key) {
        SiteData data;
        if (session.isModified()) {
            data = loadSite(session, key);
        } else {
            data = getSite(session, key);
        }

        //
        return data;
    }

    protected final SiteData loadSite(POMSession session, SiteKey key) {
        Workspace workspace = session.getWorkspace();
        ObjectType<Site> objectType = Utils.objectType(key.getType());
        Site site = workspace.getSite(objectType, key.getName());
        if (site != null) {
            return new SiteData(site);
        } else {
            return SiteData.EMPTY;
        }
    }
}