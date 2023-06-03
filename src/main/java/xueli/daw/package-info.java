/**
 * <p>This is a little DAW where several generator channels can be added.</p>
 * 
 * <p>Each channel has a chain. The beginning item in the chain received MIDI signal, acting as the beginning generator, and the last one should yield sound signal, which is actually byte buffer managed in the context.</p>
 * 
 */
package xueli.daw;