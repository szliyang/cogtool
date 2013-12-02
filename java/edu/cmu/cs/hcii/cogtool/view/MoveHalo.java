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

package edu.cmu.cs.hcii.cogtool.view;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

import edu.cmu.cs.hcii.cogtool.model.FrameElement;
import edu.cmu.cs.hcii.cogtool.util.PrecisionUtilities;
import edu.cmu.cs.hcii.cogtool.util.WindowUtil;

/**
 * A halo that appears around widgets or widget groups when selected.  It is
 * used for moving widgets as opposed to reordering them, as well as for
 * displaying which widgets are selected.
 *
 * @author rmyers
 */

public class MoveHalo extends ScalableInteractiveRectangle
{
    // color used when halo surrounds a fixed group like menu headers
    public static final Color FIXED_COLOR = ColorConstants.gray;

    public static final int BORDER_WIDTH = ResizeThumb.WIDTH_HEIGHT / 2 + 2;

    protected FrameElement data;
    protected LineBorder border;
    protected Color currentColor;

    public MoveHalo(FrameElement haloData)
    {
        super();

        setOutline(false);
        setOpaque(false);
        setFill(false);
        setCursor(WindowUtil.getCursor(WindowUtil.DRAW_CURSOR));

        currentColor = FIXED_COLOR;
        border = new LineBorder(currentColor, BORDER_WIDTH);
        setBorder(border);

        setData(haloData);
    }

    public FrameElement getData()
    {
        return data;
    }

    public void setData(FrameElement newData)
    {
        data = newData;
    }

    @Override
    public void addToEditor(InteractionDrawingEditor editor)
    {
        addToEditor(editor, 2);
    }

    @Override
    public void setScale(double newScale)
    {
        scale = newScale;

        Rectangle scaled =
            PrecisionUtilities.getDraw2DRectangle(bds.x * scale,
                                                  bds.y * scale,
                                                  bds.width * scale,
                                                  bds.height * scale);

        scaled.x -= (ResizeThumb.WIDTH_HEIGHT / 2) + 2;
        scaled.y -= (ResizeThumb.WIDTH_HEIGHT / 2) + 2;
        scaled.width += ResizeThumb.WIDTH_HEIGHT + 4;
        scaled.height += ResizeThumb.WIDTH_HEIGHT + 4;

        setBounds(scaled);
    }

    public boolean isWithinBorder(int x, int y)
    {
        Rectangle bds = getBounds();

        if ((x >= bds.x) && (y >= bds.y)) {
            int bdsRight = bds.x + bds.width;
            int bdsBottom = bds.y + bds.height;

            if (x < (bds.x + BORDER_WIDTH)) {
                return y < bdsBottom;
            }

            if (y < (bds.y + BORDER_WIDTH)) {
                return x < bdsRight;
            }

            if ((x < bdsRight) && (y < bdsBottom)) {
                return (x >= (bdsRight - BORDER_WIDTH)) ||
                       (y >= (bdsBottom - BORDER_WIDTH));
            }
        }

        return false;
    }
}