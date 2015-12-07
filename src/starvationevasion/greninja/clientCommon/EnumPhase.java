package starvationevasion.greninja.clientCommon;

/**
 * Enum of phase names.
 */
public enum EnumPhase
{
  // Drafting phase
  DRAFTING
  {
    public String toString()
        {
          return "Policy Drafting Phase";
        }
  },

  // Voting phase
  VOTING
  {
    public String toString()
        {
          return "Policy Voting Phase";
        }
  }
}
