package org.melati.poem;

import java.util.*;

/**
 * Class representing what to do about references to a <TT>Persistent</TT>
 * which is about to be deleted.
 *
 * Three canned <TT>IntegrityFix</TT> policies are provided: {@link
 * StandardIntegrityFix#delete}, which simply deletes all referring objects,
 * {@link StandardIntegrityFix#clear}, which <TT>NULL</TT>s out all
 * references, and {@link StandardIntegrityFix#prevent}, which prevents
 * deletion if there are any references which would be left dangling.
 */

public interface IntegrityFix {

  /**
   * Do something about references from a given column to a
   * <TT>Persistent</TT> which is about to be deleted.  Called via {@link
   * Persistent#deleteAndCommit}, this gives the application programmer 
   * fine-grained control over how referential integrity is maintained.
   *
   * FIXME It's critically important that the application programmer
   * really does do something about maintaining integrity, since we
   * don't do any re-checking afterwards.
   *
   * @param referee   The object which is about to be deleted.

   * @param column    A column which refers to <TT>referee</TT>'s table:
   *                  <TT>column.getType()</TT> is a
   *                  <TT>ReferencePoemType</TT> with <TT>targetTable()</TT>
   *                  <TT>==</TT> <TT>referee.getTable()</TT>.
   *
   * @param refs      All the <TT>Persistent</TT>s of which <TT>column</TT>
   *                  actually points to <TT>referee</TT>.
   *
   * @param referenceFixOfColumn The column-to-<TT>IntegrityFix</TT>
   *         mapping passed into {@link Persistent#deleteAndCommit}.
   *
   * @return A list of the <TT>Persistent</TT>s from <TT>refs</TT> which
   *         constitute reasons why the <TT>referee</TT> can't be deleted
   *         at this time.  If you want to allow the <TT>referee</TT> to be
   *         deleted, return an empty list (<TT>EmptyEnumeration.it</TT>).
   */

  Enumeration referencesTo(Persistent referee, Column column,
                           Enumeration refs, Map referenceFixOfColumn);
}
