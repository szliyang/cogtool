/*******************************************************************************
 * CogTool Copyright Notice and Distribution Terms
 * CogTool 1.2, Copyright (c) 2005-2012 Carnegie Mellon University
 * This software is distributed under the terms of the FSF Lesser
 * Gnu Public License (see LGPL.txt). 
 * 
 * CogTool is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 * 
 * CogTool is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with CogTool; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * CogTool makes use of several third-party components, with the 
 * following notices:
 * 
 * Eclipse SWT
 * Eclipse GEF Draw2D
 * 
 * Unless otherwise indicated, all Content made available by the Eclipse 
 * Foundation is provided to you under the terms and conditions of the Eclipse 
 * Public License Version 1.0 ("EPL"). A copy of the EPL is provided with this 
 * Content and is also available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * CLISP
 * 
 * Copyright (c) Sam Steingold, Bruno Haible 2001-2006
 * This software is distributed under the terms of the FSF Gnu Public License.
 * See COPYRIGHT file in clisp installation folder for more information.
 * 
 * ACT-R 6.0
 * 
 * Copyright (c) 1998-2007 Dan Bothell, Mike Byrne, Christian Lebiere & 
 *                         John R Anderson. 
 * This software is distributed under the terms of the FSF Lesser
 * Gnu Public License (see LGPL.txt).
 * 
 * Apache Jakarta Commons-Lang 2.1
 * 
 * This product contains software developed by the Apache Software Foundation
 * (http://www.apache.org/)
 * 
 * Mozilla XULRunner 1.9.0.5
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/.
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The J2SE(TM) Java Runtime Environment
 * 
 * Copyright 2009 Sun Microsystems, Inc., 4150
 * Network Circle, Santa Clara, California 95054, U.S.A.  All
 * rights reserved. U.S.  
 * See the LICENSE file in the jre folder for more information.
 ******************************************************************************/

package edu.cmu.cs.hcii.cogtool.model;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs.hcii.cogtool.util.ObjectLoader;
import edu.cmu.cs.hcii.cogtool.util.ObjectSaver;

public class PullDownItem extends Widget implements ChildWidget, TraversableWidget
{
    public static final int edu_cmu_cs_hcii_cogtool_model_PullDownItem_version = 0;

    protected static final String parentVAR = "parent";

    private static ObjectSaver.IDataSaver<PullDownItem> SAVER =
        new ObjectSaver.ADataSaver<PullDownItem>() {
            @Override
            public int getVersion()
            {
                return edu_cmu_cs_hcii_cogtool_model_PullDownItem_version;
            }

            @Override
            public void saveData(PullDownItem v, ObjectSaver saver)
                throws java.io.IOException
            {
                saver.saveObject(v.parent, parentVAR);
            }
        };

    public static void registerSaver()
    {
        ObjectSaver.registerSaver(PullDownItem.class.getName(), SAVER);
    }

    private static ObjectLoader.IObjectLoader<PullDownItem> LOADER =
        new ObjectLoader.AObjectLoader<PullDownItem>() {
            @Override
            public PullDownItem createObject()
            {
                return new PullDownItem();
            }

            @Override
            public void set(PullDownItem target, String variable, Object value)
            {
                if (variable != null) {
                    if (variable.equals(parentVAR)) {
                        target.parent = (PullDownHeader) value;
                    }
                }
            }
        };

    public static void registerLoader()
    {
        ObjectLoader.registerLoader(PullDownItem.class.getName(),
                                    edu_cmu_cs_hcii_cogtool_model_PullDownItem_version,
                                    LOADER);
    }

    protected PullDownHeader parent;

    protected PullDownItem()
    {
        // For loading
        super();
    }

    public PullDownItem(PullDownHeader header,
                        DoubleRectangle bounds,
                        String useTitle)
    {
        super(bounds, WidgetType.PullDownItem);

        parent = header;
        parent.addItem(this);

        setTitle(useTitle);
    }

    /**
     * Returns true if is a standard widget, false if a custom widget
     */
    @Override
    public boolean isStandard()
    {
        return true;
    }


    public AParentWidget getParent()
    {
        return parent;
    }


    public void setParent(AParentWidget pullDownParent)
    {
        parent = (PullDownHeader) pullDownParent;
    }

    @Override
    public FrameElement getRootElement()
    {
        return parent.getRootElement();
    }

    /**
     * Looks at all of the transitions of all of its parent's children and finds
     * the smallest curve index not in use
     */
    @Override
    public void assignCurveIndex(Transition t)
    {
        // TODO It should be impossible for parent to be null here. However, because
        //      of a bug, that should be fixed, it can be null when importing from
        //      XML. This appears to be because setting the parent is deferred
        //      there, but setting up the transitions is not deferred; it probably
        //      should be.  Ticket #775.
        if (parent == null) {
            return;
        }
        List<Transition> allTransitions = new ArrayList<Transition>();
        parent.addAllTransitions(allTransitions);
        int[] indexes = new int[allTransitions.size()];
        t.setCurveIndex(computeCurveIndex(t, allTransitions.iterator(), indexes));
    }

    /**
     * Create a "deep" copy of this widget.
     * <p>
     * It is the responsibility of the caller to "place" the copy
     * (usually by adding it to an Frame).
     *
     * @param duplicator the manager of duplicate Frame instances
     * @return the widget copy
     * @author mlh
     */

    public ChildWidget duplicate(AParentWidget copyParent,
                                  Frame.IFrameDuplicator duplicator,
                                  SimpleWidgetGroup.IWidgetDuplicator situator)
    {
        PullDownItem widgetCopy = new PullDownItem();

        widgetCopy.setParent(copyParent);

        widgetCopy.copyState(this, duplicator);

        situator.placeInContext(this, widgetCopy);

        return widgetCopy;
    }

    /**
     * Overridden to ensure it is never called with this signature.
     */
    @Override
    public IWidget duplicate(Frame.IFrameDuplicator duplicator)
    {
        throw new IllegalStateException("Should never be called on this instance.");
    }


    public ChildWidget duplicate(AParentWidget copyParent,
                                  Frame.IFrameDuplicator duplicator,
                                  SimpleWidgetGroup.IWidgetDuplicator situator,
                                  int insertIndex)
    {
        ChildWidget childCopy = duplicate(copyParent, duplicator, situator);

        copyParent.addItem(insertIndex, childCopy);

        return childCopy;
    }
}