/*
 * Copyright (c) 2003-2005 The BISON Project
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */

package peersim.core.dcdatastore;

import peersim.core.CommonState;

/**
 * This is the common state of the simulation all objects see.
 * Static singleton. One of its purposes is
 * simplification of parameter structures and increasing efficiency by putting
 * state information here instead of passing parameters.
 *<p>
 * <em>The set methods should not be used by applications</em>,
 * they are for system
 * components. Use them only if you know exactly what you are doing, e.g.
 * if you are so advanced that you can write your own simulation engine.
 * Ideally, they should not be visible, but due to the lack of more
 * flexibility in java access rights, we are forced to make them public.
 */
public class DCCommonState extends CommonState
{

//======================= constants ===============================
//=================================================================

//======================= variables ===============================
//=================================================================

static private ServerNode globalServer;

/** Does nothing. To avoid construction but allow extension. */
protected DCCommonState() {
	super();
	DCCommonState.globalServer = null;
}

// ======================= methods =================================
// =================================================================

public static void setGlobalServerInstance(ServerNode global) {
	DCCommonState.globalServer = global;
}

public static ServerNode globalServer() {
	return DCCommonState.globalServer;
}

}


