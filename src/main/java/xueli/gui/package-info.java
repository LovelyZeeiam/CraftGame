/**
 * Make a Gui framework by myself because my game engine need this.
 *
 * <p>Here some principles are defined explicitly:</p>
 * <ul>
 * 	    <li>1. Every widgets should have a interface that play the role of Controller, and the controller can only be an interface to change the model of a controller.</li>
 * 	    <li>2. The painting API should be hidden from the external access, the user can only create a widget at a manager and get its controller. Requests of changing skin should be submitted to the manager.</li>
 * </ul>
 *
 * <p>Not all widgets are provided. Only widgets used are provided.</p>
 *
 * To avoid code being removed, a new package "gui2" is used.
 *
 */
package xueli.gui;