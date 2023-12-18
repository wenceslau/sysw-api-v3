package com.sysw.suite.core.domain;


/*
In the context of Domain-Driven Design (DDD), the concepts of AggregateRoot and Entity are fundamental building blocks:

Entity: An entity, also known as a reference object, is an object that has a distinct identity that runs through time
and different representations. What distinguishes each entity from another is not its attributes, but its continuity and
its identity. For example, two people can have the same name, birth date, and other attributes, but they are distinct
individuals, identifiable by a social security number or other unique identifier.
Importantly, the identity of entities must be consistent across all references, within any given context,
because they are mutable and their state can change over time.

AggregateRoot: Aggregates are a collection of related entities and value objects that are treated as a single unit.
The root of the aggregate is a specific entity, known as the Aggregate Root. The aggregate root is the only member of
the aggregate that outside objects are allowed to hold references to, while communication or changes to the other
members of the aggregate can only be performed through the aggregate root. Any rules that apply to the aggregate as a
whole, are enforced in the aggregate root.

This approach is intended to provide a certain degree of encapsulation to a group of entities and value objects.
They can maintain their true invariants while interacting within the boundary. For an example, consider an Order and
OrderLine in an e-commerce system. Here Order is an aggregate root, and OrderLine is an entity. Here OrderLine can't
exist without Order. All operations and rules on OrderLine are maintained by Order.

These concepts help to maintain complex domain models by ensuring the integrity of the whole system in an object-oriented
way. They provide consistency, isolation, and encapsulation, which are essential properties for maintaining complicated
business logic and rules.
 */
public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID>{

    public AggregateRoot(final ID id) {
        super(id);
    }
}
